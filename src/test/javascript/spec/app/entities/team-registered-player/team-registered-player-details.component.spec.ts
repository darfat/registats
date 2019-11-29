/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TeamRegisteredPlayerDetailComponent from '@/entities/team-registered-player/team-registered-player-details.vue';
import TeamRegisteredPlayerClass from '@/entities/team-registered-player/team-registered-player-details.component';
import TeamRegisteredPlayerService from '@/entities/team-registered-player/team-registered-player.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TeamRegisteredPlayer Management Detail Component', () => {
    let wrapper: Wrapper<TeamRegisteredPlayerClass>;
    let comp: TeamRegisteredPlayerClass;
    let teamRegisteredPlayerServiceStub: SinonStubbedInstance<TeamRegisteredPlayerService>;

    beforeEach(() => {
      teamRegisteredPlayerServiceStub = sinon.createStubInstance<TeamRegisteredPlayerService>(TeamRegisteredPlayerService);

      wrapper = shallowMount<TeamRegisteredPlayerClass>(TeamRegisteredPlayerDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { teamRegisteredPlayerService: () => teamRegisteredPlayerServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeamRegisteredPlayer = { id: 123 };
        teamRegisteredPlayerServiceStub.find.resolves(foundTeamRegisteredPlayer);

        // WHEN
        comp.retrieveTeamRegisteredPlayer(123);
        await comp.$nextTick();

        // THEN
        expect(comp.teamRegisteredPlayer).toBe(foundTeamRegisteredPlayer);
      });
    });
  });
});
