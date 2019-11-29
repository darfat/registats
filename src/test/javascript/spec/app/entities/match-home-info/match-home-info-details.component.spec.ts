/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MatchHomeInfoDetailComponent from '@/entities/match-home-info/match-home-info-details.vue';
import MatchHomeInfoClass from '@/entities/match-home-info/match-home-info-details.component';
import MatchHomeInfoService from '@/entities/match-home-info/match-home-info.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchHomeInfo Management Detail Component', () => {
    let wrapper: Wrapper<MatchHomeInfoClass>;
    let comp: MatchHomeInfoClass;
    let matchHomeInfoServiceStub: SinonStubbedInstance<MatchHomeInfoService>;

    beforeEach(() => {
      matchHomeInfoServiceStub = sinon.createStubInstance<MatchHomeInfoService>(MatchHomeInfoService);

      wrapper = shallowMount<MatchHomeInfoClass>(MatchHomeInfoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { matchHomeInfoService: () => matchHomeInfoServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchHomeInfo = { id: 123 };
        matchHomeInfoServiceStub.find.resolves(foundMatchHomeInfo);

        // WHEN
        comp.retrieveMatchHomeInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchHomeInfo).toBe(foundMatchHomeInfo);
      });
    });
  });
});
