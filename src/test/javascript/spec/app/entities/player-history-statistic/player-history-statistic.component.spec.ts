/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PlayerHistoryStatisticComponent from '@/entities/player-history-statistic/player-history-statistic.vue';
import PlayerHistoryStatisticClass from '@/entities/player-history-statistic/player-history-statistic.component';
import PlayerHistoryStatisticService from '@/entities/player-history-statistic/player-history-statistic.service';

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
  describe('PlayerHistoryStatistic Management Component', () => {
    let wrapper: Wrapper<PlayerHistoryStatisticClass>;
    let comp: PlayerHistoryStatisticClass;
    let playerHistoryStatisticServiceStub: SinonStubbedInstance<PlayerHistoryStatisticService>;

    beforeEach(() => {
      playerHistoryStatisticServiceStub = sinon.createStubInstance<PlayerHistoryStatisticService>(PlayerHistoryStatisticService);
      playerHistoryStatisticServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PlayerHistoryStatisticClass>(PlayerHistoryStatisticComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          playerHistoryStatisticService: () => playerHistoryStatisticServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      playerHistoryStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPlayerHistoryStatistics();
      await comp.$nextTick();

      // THEN
      expect(playerHistoryStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.playerHistoryStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      playerHistoryStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(playerHistoryStatisticServiceStub.retrieve.called).toBeTruthy();
      expect(comp.playerHistoryStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      playerHistoryStatisticServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(playerHistoryStatisticServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      playerHistoryStatisticServiceStub.retrieve.reset();
      playerHistoryStatisticServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(playerHistoryStatisticServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.playerHistoryStatistics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      playerHistoryStatisticServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePlayerHistoryStatistic();
      await comp.$nextTick();

      // THEN
      expect(playerHistoryStatisticServiceStub.delete.called).toBeTruthy();
      expect(playerHistoryStatisticServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
