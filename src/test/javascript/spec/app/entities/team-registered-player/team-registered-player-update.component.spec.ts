/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TeamRegisteredPlayerUpdateComponent from '@/entities/team-registered-player/team-registered-player-update.vue';
import TeamRegisteredPlayerClass from '@/entities/team-registered-player/team-registered-player-update.component';
import TeamRegisteredPlayerService from '@/entities/team-registered-player/team-registered-player.service';

import TeamService from '@/entities/team/team.service';

import PlayerService from '@/entities/player/player.service';

import CompetitionService from '@/entities/competition/competition.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TeamRegisteredPlayer Management Update Component', () => {
    let wrapper: Wrapper<TeamRegisteredPlayerClass>;
    let comp: TeamRegisteredPlayerClass;
    let teamRegisteredPlayerServiceStub: SinonStubbedInstance<TeamRegisteredPlayerService>;

    beforeEach(() => {
      teamRegisteredPlayerServiceStub = sinon.createStubInstance<TeamRegisteredPlayerService>(TeamRegisteredPlayerService);

      wrapper = shallowMount<TeamRegisteredPlayerClass>(TeamRegisteredPlayerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          teamRegisteredPlayerService: () => teamRegisteredPlayerServiceStub,

          teamService: () => new TeamService(),

          playerService: () => new PlayerService(),

          competitionService: () => new CompetitionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.teamRegisteredPlayer = entity;
        teamRegisteredPlayerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamRegisteredPlayerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.teamRegisteredPlayer = entity;
        teamRegisteredPlayerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(teamRegisteredPlayerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
