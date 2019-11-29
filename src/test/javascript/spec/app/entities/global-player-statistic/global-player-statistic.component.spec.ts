/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GlobalPlayerStatisticComponent from '@/entities/global-player-statistic/global-player-statistic.vue';
import GlobalPlayerStatisticClass from '@/entities/global-player-statistic/global-player-statistic.component';
import GlobalPlayerStatisticService from '@/entities/global-player-statistic/global-player-statistic.service';

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
  describe('GlobalPlayerStatistic Management Component', () => {
    let wrapper: Wrapper<GlobalPlayerStatisticClass>;
    let comp: GlobalPlayerStatisticClass;
    let globalPlayerStatisticServiceStub: SinonStubbedInstance<GlobalPlayerStatisticService>;

    beforeEach(() => {
      globalPlayerStatisticServiceStub = sinon.createStubInstance<GlobalPlayerStatisticService>(GlobalPlayerStatisticService);
      globalPlayerStatisticServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GlobalPlayerStatisticClass>(GlobalPlayerStatisticComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          globalPlayerStatisticService: () => globalPlayerStatisticServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      globalPlayerStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGlobalPlayerStatistics();
      await comp.$nextTick();

      // THEN
      expect(globalPlayerStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.globalPlayerStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      globalPlayerStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(globalPlayerStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.globalPlayerStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      globalPlayerStatisticServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(globalPlayerStatisticServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      globalPlayerStatisticServiceStub.retrieve.reset();
      globalPlayerStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(globalPlayerStatisticServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.globalPlayerStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      globalPlayerStatisticServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGlobalPlayerStatistic();
      await comp.$nextTick();

      // THEN
      expect(globalPlayerStatisticServiceStub.delete.called).toBeTruthy();
      expect(globalPlayerStatisticServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
