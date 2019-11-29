/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitionStatisticItemDetailComponent from '@/entities/competition-statistic-item/competition-statistic-item-details.vue';
import CompetitionStatisticItemClass from '@/entities/competition-statistic-item/competition-statistic-item-details.component';
import CompetitionStatisticItemService from '@/entities/competition-statistic-item/competition-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitionStatisticItem Management Detail Component', () => {
    let wrapper: Wrapper<CompetitionStatisticItemClass>;
    let comp: CompetitionStatisticItemClass;
    let competitionStatisticItemServiceStub: SinonStubbedInstance<CompetitionStatisticItemService>;

    beforeEach(() => {
      competitionStatisticItemServiceStub = sinon.createStubInstance<CompetitionStatisticItemService>(CompetitionStatisticItemService);

      wrapper = shallowMount<CompetitionStatisticItemClass>(CompetitionStatisticItemDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitionStatisticItemService: () => competitionStatisticItemServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitionStatisticItem = { id: 123 };
        competitionStatisticItemServiceStub.find.resolves(foundCompetitionStatisticItem);

        // WHEN
        comp.retrieveCompetitionStatisticItem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitionStatisticItem).toBe(foundCompetitionStatisticItem);
      });
    });
  });
});
