/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PlayerStatisticItemComponent from '@/entities/player-statistic-item/player-statistic-item.vue';
import PlayerStatisticItemClass from '@/entities/player-statistic-item/player-statistic-item.component';
import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';

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
  describe('PlayerStatisticItem Management Component', () => {
    let wrapper: Wrapper<PlayerStatisticItemClass>;
    let comp: PlayerStatisticItemClass;
    let playerStatisticItemServiceStub: SinonStubbedInstance<PlayerStatisticItemService>;

    beforeEach(() => {
      playerStatisticItemServiceStub = sinon.createStubInstance<PlayerStatisticItemService>(PlayerStatisticItemService);
      playerStatisticItemServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PlayerStatisticItemClass>(PlayerStatisticItemComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          playerStatisticItemService: () => playerStatisticItemServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      playerStatisticItemServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPlayerStatisticItems();
      await comp.$nextTick();

      // THEN
      expect(playerStatisticItemServiceStub.retrieve.called).toBeTruthy();
      expect(comp.playerStatisticItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      playerStatisticItemServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(playerStatisticItemServiceStub.retrieve.called).toBeTruthy();
      expect(comp.playerStatisticItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      playerStatisticItemServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(playerStatisticItemServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      playerStatisticItemServiceStub.retrieve.reset();
      playerStatisticItemServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(playerStatisticItemServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.playerStatisticItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      playerStatisticItemServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePlayerStatisticItem();
      await comp.$nextTick();

      // THEN
      expect(playerStatisticItemServiceStub.delete.called).toBeTruthy();
      expect(playerStatisticItemServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
