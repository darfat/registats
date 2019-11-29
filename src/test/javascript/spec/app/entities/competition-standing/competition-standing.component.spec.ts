/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionStandingComponent from '@/entities/competition-standing/competition-standing.vue';
import CompetitionStandingClass from '@/entities/competition-standing/competition-standing.component';
import CompetitionStandingService from '@/entities/competition-standing/competition-standing.service';

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
  describe('CompetitionStanding Management Component', () => {
    let wrapper: Wrapper<CompetitionStandingClass>;
    let comp: CompetitionStandingClass;
    let competitionStandingServiceStub: SinonStubbedInstance<CompetitionStandingService>;

    beforeEach(() => {
      competitionStandingServiceStub = sinon.createStubInstance<CompetitionStandingService>(CompetitionStandingService);
      competitionStandingServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompetitionStandingClass>(CompetitionStandingComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          competitionStandingService: () => competitionStandingServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      competitionStandingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompetitionStandings();
      await comp.$nextTick();

      // THEN
      expect(competitionStandingServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionStandings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      competitionStandingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(competitionStandingServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionStandings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      competitionStandingServiceStub.retrieve.reset();
      competitionStandingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(competitionStandingServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.competitionStandings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      competitionStandingServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompetitionStanding();
      await comp.$nextTick();

      // THEN
      expect(competitionStandingServiceStub.delete.called).toBeTruthy();
      expect(competitionStandingServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
