/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitionStandingDetailComponent from '@/entities/competition-standing/competition-standing-details.vue';
import CompetitionStandingClass from '@/entities/competition-standing/competition-standing-details.component';
import CompetitionStandingService from '@/entities/competition-standing/competition-standing.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitionStanding Management Detail Component', () => {
    let wrapper: Wrapper<CompetitionStandingClass>;
    let comp: CompetitionStandingClass;
    let competitionStandingServiceStub: SinonStubbedInstance<CompetitionStandingService>;

    beforeEach(() => {
      competitionStandingServiceStub = sinon.createStubInstance<CompetitionStandingService>(CompetitionStandingService);

      wrapper = shallowMount<CompetitionStandingClass>(CompetitionStandingDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitionStandingService: () => competitionStandingServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitionStanding = { id: 123 };
        competitionStandingServiceStub.find.resolves(foundCompetitionStanding);

        // WHEN
        comp.retrieveCompetitionStanding(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitionStanding).toBe(foundCompetitionStanding);
      });
    });
  });
});
