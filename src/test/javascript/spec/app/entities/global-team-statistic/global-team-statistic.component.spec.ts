/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GlobalTeamStatisticComponent from '@/entities/global-team-statistic/global-team-statistic.vue';
import GlobalTeamStatisticClass from '@/entities/global-team-statistic/global-team-statistic.component';
import GlobalTeamStatisticService from '@/entities/global-team-statistic/global-team-statistic.service';

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
  describe('GlobalTeamStatistic Management Component', () => {
    let wrapper: Wrapper<GlobalTeamStatisticClass>;
    let comp: GlobalTeamStatisticClass;
    let globalTeamStatisticServiceStub: SinonStubbedInstance<GlobalTeamStatisticService>;

    beforeEach(() => {
      globalTeamStatisticServiceStub = sinon.createStubInstance<GlobalTeamStatisticService>(GlobalTeamStatisticService);
      globalTeamStatisticServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GlobalTeamStatisticClass>(GlobalTeamStatisticComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          globalTeamStatisticService: () => globalTeamStatisticServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      globalTeamStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGlobalTeamStatistics();
      await comp.$nextTick();

      // THEN
      expect(globalTeamStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.globalTeamStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      globalTeamStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(globalTeamStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.globalTeamStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      globalTeamStatisticServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(globalTeamStatisticServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      globalTeamStatisticServiceStub.retrieve.reset();
      globalTeamStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(globalTeamStatisticServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.globalTeamStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      globalTeamStatisticServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGlobalTeamStatistic();
      await comp.$nextTick();

      // THEN
      expect(globalTeamStatisticServiceStub.delete.called).toBeTruthy();
      expect(globalTeamStatisticServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
