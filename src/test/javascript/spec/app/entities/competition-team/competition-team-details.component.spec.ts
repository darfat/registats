/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitionTeamDetailComponent from '@/entities/competition-team/competition-team-details.vue';
import CompetitionTeamClass from '@/entities/competition-team/competition-team-details.component';
import CompetitionTeamService from '@/entities/competition-team/competition-team.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitionTeam Management Detail Component', () => {
    let wrapper: Wrapper<CompetitionTeamClass>;
    let comp: CompetitionTeamClass;
    let competitionTeamServiceStub: SinonStubbedInstance<CompetitionTeamService>;

    beforeEach(() => {
      competitionTeamServiceStub = sinon.createStubInstance<CompetitionTeamService>(CompetitionTeamService);

      wrapper = shallowMount<CompetitionTeamClass>(CompetitionTeamDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitionTeamService: () => competitionTeamServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitionTeam = { id: 123 };
        competitionTeamServiceStub.find.resolves(foundCompetitionTeam);

        // WHEN
        comp.retrieveCompetitionTeam(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitionTeam).toBe(foundCompetitionTeam);
      });
    });
  });
});
