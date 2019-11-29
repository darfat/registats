/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompetitionTeamComponent from '@/entities/competition-team/competition-team.vue';
import CompetitionTeamClass from '@/entities/competition-team/competition-team.component';
import CompetitionTeamService from '@/entities/competition-team/competition-team.service';

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
  describe('CompetitionTeam Management Component', () => {
    let wrapper: Wrapper<CompetitionTeamClass>;
    let comp: CompetitionTeamClass;
    let competitionTeamServiceStub: SinonStubbedInstance<CompetitionTeamService>;

    beforeEach(() => {
      competitionTeamServiceStub = sinon.createStubInstance<CompetitionTeamService>(CompetitionTeamService);
      competitionTeamServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompetitionTeamClass>(CompetitionTeamComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          competitionTeamService: () => competitionTeamServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      competitionTeamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCompetitionTeams();
      await comp.$nextTick();

      // THEN
      expect(competitionTeamServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionTeams[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      competitionTeamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(competitionTeamServiceStub.retrieve.called).toBeTruthy();
      expect(comp.competitionTeams[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      competitionTeamServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(competitionTeamServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      competitionTeamServiceStub.retrieve.reset();
      competitionTeamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(competitionTeamServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.competitionTeams[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      competitionTeamServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCompetitionTeam();
      await comp.$nextTick();

      // THEN
      expect(competitionTeamServiceStub.delete.called).toBeTruthy();
      expect(competitionTeamServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
