/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GlobalTeamStatisticUpdateComponent from '@/entities/global-team-statistic/global-team-statistic-update.vue';
import GlobalTeamStatisticClass from '@/entities/global-team-statistic/global-team-statistic-update.component';
import GlobalTeamStatisticService from '@/entities/global-team-statistic/global-team-statistic.service';

import TeamService from '@/entities/team/team.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('GlobalTeamStatistic Management Update Component', () => {
    let wrapper: Wrapper<GlobalTeamStatisticClass>;
    let comp: GlobalTeamStatisticClass;
    let globalTeamStatisticServiceStub: SinonStubbedInstance<GlobalTeamStatisticService>;

    beforeEach(() => {
      globalTeamStatisticServiceStub = sinon.createStubInstance<GlobalTeamStatisticService>(GlobalTeamStatisticService);

      wrapper = shallowMount<GlobalTeamStatisticClass>(GlobalTeamStatisticUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          globalTeamStatisticService: () => globalTeamStatisticServiceStub,

          teamService: () => new TeamService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.globalTeamStatistic = entity;
        globalTeamStatisticServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(globalTeamStatisticServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.globalTeamStatistic = entity;
        globalTeamStatisticServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(globalTeamStatisticServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
