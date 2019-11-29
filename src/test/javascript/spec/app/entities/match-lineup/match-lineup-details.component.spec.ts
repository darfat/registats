/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MatchLineupDetailComponent from '@/entities/match-lineup/match-lineup-details.vue';
import MatchLineupClass from '@/entities/match-lineup/match-lineup-details.component';
import MatchLineupService from '@/entities/match-lineup/match-lineup.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchLineup Management Detail Component', () => {
    let wrapper: Wrapper<MatchLineupClass>;
    let comp: MatchLineupClass;
    let matchLineupServiceStub: SinonStubbedInstance<MatchLineupService>;

    beforeEach(() => {
      matchLineupServiceStub = sinon.createStubInstance<MatchLineupService>(MatchLineupService);

      wrapper = shallowMount<MatchLineupClass>(MatchLineupDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { matchLineupService: () => matchLineupServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchLineup = { id: 123 };
        matchLineupServiceStub.find.resolves(foundMatchLineup);

        // WHEN
        comp.retrieveMatchLineup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchLineup).toBe(foundMatchLineup);
      });
    });
  });
});
