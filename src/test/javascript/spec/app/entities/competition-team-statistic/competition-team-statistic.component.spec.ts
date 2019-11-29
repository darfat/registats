/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionTeamStatisticComponent from '@/entities/competition-team-statistic/competition-team-statistic.vue';
import CompetitionTeamStatisticClass from '@/entities/competition-team-statistic/competition-team-statistic.component';
import CompetitionTeamStatisticService from '@/entities/competition-team-statistic/competition-team-statistic.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('CompetitionTeamStatistic Management Component', () => {
    let wrapper: Wrapper<CompetitionTeamStatisticClass>;
    let comp: CompetitionTeamStatisticClass;
    let competitionTeamStatisticServiceStub: SinonStubbedInstance<CompetitionTeamStatisticService>;

    beforeEach(() => {
      competitionTeamStatisticServiceStub = sinon.createStubInstance<CompetitionTeamStatisticService>(CompetitionTeamStatisticService);
      competitionTeamStatisticServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompetitionTeamStatisticClass>(CompetitionTeamStatisticComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          competitionTeamStatisticService: () => competitionTeamStatisticServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      competitionTeamStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompetitionTeamStatistics();
      await comp.$nextTick();

      // THEN
      expect(competitionTeamStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionTeamStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      competitionTeamStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(competitionTeamStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionTeamStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      competitionTeamStatisticServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(competitionTeamStatisticServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      competitionTeamStatisticServiceStub.retrieve.reset();
      competitionTeamStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(competitionTeamStatisticServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.competitionTeamStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      competitionTeamStatisticServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompetitionTeamStatistic();
      await comp.$nextTick();

      // THEN
      expect(competitionTeamStatisticServiceStub.delete.called).toBeTruthy();
      expect(competitionTeamStatisticServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
