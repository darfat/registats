/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MatchDetailComponent from '@/entities/match/match-details.vue';
import MatchClass from '@/entities/match/match-details.component';
import MatchService from '@/entities/match/match.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Match Management Detail Component', () => {
    let wrapper: Wrapper<MatchClass>;
    let comp: MatchClass;
    let matchServiceStub: SinonStubbedInstance<MatchService>;

    beforeEach(() => {
      matchServiceStub = sinon.createStubInstance<MatchService>(MatchService);

      wrapper = shallowMount<MatchClass>(MatchDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { matchService: () => matchServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatch = { id: 123 };
        matchServiceStub.find.resolves(foundMatch);

        // WHEN
        comp.retrieveMatch(123);
        await comp.$nextTick();

        // THEN
        expect(comp.match).toBe(foundMatch);
      });
    });
  });
});
