/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MatchStatisticItemDetailComponent from '@/entities/match-statistic-item/match-statistic-item-details.vue';
import MatchStatisticItemClass from '@/entities/match-statistic-item/match-statistic-item-details.component';
import MatchStatisticItemService from '@/entities/match-statistic-item/match-statistic-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchStatisticItem Management Detail Component', () => {
    let wrapper: Wrapper<MatchStatisticItemClass>;
    let comp: MatchStatisticItemClass;
    let matchStatisticItemServiceStub: SinonStubbedInstance<MatchStatisticItemService>;

    beforeEach(() => {
      matchStatisticItemServiceStub = sinon.createStubInstance<MatchStatisticItemService>(MatchStatisticItemService);

      wrapper = shallowMount<MatchStatisticItemClass>(MatchStatisticItemDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { matchStatisticItemService: () => matchStatisticItemServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchStatisticItem = { id: 123 };
        matchStatisticItemServiceStub.find.resolves(foundMatchStatisticItem);

        // WHEN
        comp.retrieveMatchStatisticItem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchStatisticItem).toBe(foundMatchStatisticItem);
      });
    });
  });
});
