/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionPlayerStatisticComponent from '@/entities/competition-player-statistic/competition-player-statistic.vue';
import CompetitionPlayerStatisticClass from '@/entities/competition-player-statistic/competition-player-statistic.component';
import CompetitionPlayerStatisticService from '@/entities/competition-player-statistic/competition-player-statistic.service';

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
  describe('CompetitionPlayerStatistic Management Component', () => {
    let wrapper: Wrapper<CompetitionPlayerStatisticClass>;
    let comp: CompetitionPlayerStatisticClass;
    let competitionPlayerStatisticServiceStub: SinonStubbedInstance<CompetitionPlayerStatisticService>;

    beforeEach(() => {
      competitionPlayerStatisticServiceStub = sinon.createStubInstance<CompetitionPlayerStatisticService>(
        CompetitionPlayerStatisticService
      );
      competitionPlayerStatisticServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompetitionPlayerStatisticClass>(CompetitionPlayerStatisticComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          competitionPlayerStatisticService: () => competitionPlayerStatisticServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      competitionPlayerStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompetitionPlayerStatistics();
      await comp.$nextTick();

      // THEN
      expect(competitionPlayerStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionPlayerStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      competitionPlayerStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(competitionPlayerStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionPlayerStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      competitionPlayerStatisticServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(competitionPlayerStatisticServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      competitionPlayerStatisticServiceStub.retrieve.reset();
      competitionPlayerStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(competitionPlayerStatisticServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.competitionPlayerStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      competitionPlayerStatisticServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompetitionPlayerStatistic();
      await comp.$nextTick();

      // THEN
      expect(competitionPlayerStatisticServiceStub.delete.called).toBeTruthy();
      expect(competitionPlayerStatisticServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
