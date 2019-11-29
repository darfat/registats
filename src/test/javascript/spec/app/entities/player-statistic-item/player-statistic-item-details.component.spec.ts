/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PlayerStatisticItemDetailComponent from '@/entities/player-statistic-item/player-statistic-item-details.vue';
import PlayerStatisticItemClass from '@/entities/player-statistic-item/player-statistic-item-details.component';
import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PlayerStatisticItem Management Detail Component', () => {
    let wrapper: Wrapper<PlayerStatisticItemClass>;
    let comp: PlayerStatisticItemClass;
    let playerStatisticItemServiceStub: SinonStubbedInstance<PlayerStatisticItemService>;

    beforeEach(() => {
      playerStatisticItemServiceStub = sinon.createStubInstance<PlayerStatisticItemService>(PlayerStatisticItemService);

      wrapper = shallowMount<PlayerStatisticItemClass>(PlayerStatisticItemDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { playerStatisticItemService: () => playerStatisticItemServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPlayerStatisticItem = { id: 123 };
        playerStatisticItemServiceStub.find.resolves(foundPlayerStatisticItem);

        // WHEN
        comp.retrievePlayerStatisticItem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.playerStatisticItem).toBe(foundPlayerStatisticItem);
      });
    });
  });
});
