/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitionDetailComponent from '@/entities/competition/competition-details.vue';
import CompetitionClass from '@/entities/competition/competition-details.component';
import CompetitionService from '@/entities/competition/competition.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Competition Management Detail Component', () => {
    let wrapper: Wrapper<CompetitionClass>;
    let comp: CompetitionClass;
    let competitionServiceStub: SinonStubbedInstance<CompetitionService>;

    beforeEach(() => {
      competitionServiceStub = sinon.createStubInstance<CompetitionService>(CompetitionService);

      wrapper = shallowMount<CompetitionClass>(CompetitionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitionService: () => competitionServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetition = { id: 123 };
        competitionServiceStub.find.resolves(foundCompetition);

        // WHEN
        comp.retrieveCompetition(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competition).toBe(foundCompetition);
      });
    });
  });
});
