/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MatchAwayInfoDetailComponent from '@/entities/match-away-info/match-away-info-details.vue';
import MatchAwayInfoClass from '@/entities/match-away-info/match-away-info-details.component';
import MatchAwayInfoService from '@/entities/match-away-info/match-away-info.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchAwayInfo Management Detail Component', () => {
    let wrapper: Wrapper<MatchAwayInfoClass>;
    let comp: MatchAwayInfoClass;
    let matchAwayInfoServiceStub: SinonStubbedInstance<MatchAwayInfoService>;

    beforeEach(() => {
      matchAwayInfoServiceStub = sinon.createStubInstance<MatchAwayInfoService>(MatchAwayInfoService);

      wrapper = shallowMount<MatchAwayInfoClass>(MatchAwayInfoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { matchAwayInfoService: () => matchAwayInfoServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchAwayInfo = { id: 123 };
        matchAwayInfoServiceStub.find.resolves(foundMatchAwayInfo);

        // WHEN
        comp.retrieveMatchAwayInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchAwayInfo).toBe(foundMatchAwayInfo);
      });
    });
  });
});
