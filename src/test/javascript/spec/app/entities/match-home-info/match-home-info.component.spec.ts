/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MatchHomeInfoComponent from '@/entities/match-home-info/match-home-info.vue';
import MatchHomeInfoClass from '@/entities/match-home-info/match-home-info.component';
import MatchHomeInfoService from '@/entities/match-home-info/match-home-info.service';

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
  describe('MatchHomeInfo Management Component', () => {
    let wrapper: Wrapper<MatchHomeInfoClass>;
    let comp: MatchHomeInfoClass;
    let matchHomeInfoServiceStub: SinonStubbedInstance<MatchHomeInfoService>;

    beforeEach(() => {
      matchHomeInfoServiceStub = sinon.createStubInstance<MatchHomeInfoService>(MatchHomeInfoService);
      matchHomeInfoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MatchHomeInfoClass>(MatchHomeInfoComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          matchHomeInfoService: () => matchHomeInfoServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      matchHomeInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMatchHomeInfos();
      await comp.$nextTick();

      // THEN
      expect(matchHomeInfoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.matchHomeInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      matchHomeInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(matchHomeInfoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.matchHomeInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      matchHomeInfoServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(matchHomeInfoServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      matchHomeInfoServiceStub.retrieve.reset();
      matchHomeInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(matchHomeInfoServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.matchHomeInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      matchHomeInfoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMatchHomeInfo();
      await comp.$nextTick();

      // THEN
      expect(matchHomeInfoServiceStub.delete.called).toBeTruthy();
      expect(matchHomeInfoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
