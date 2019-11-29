/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MatchCommentaryDetailComponent from '@/entities/match-commentary/match-commentary-details.vue';
import MatchCommentaryClass from '@/entities/match-commentary/match-commentary-details.component';
import MatchCommentaryService from '@/entities/match-commentary/match-commentary.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MatchCommentary Management Detail Component', () => {
    let wrapper: Wrapper<MatchCommentaryClass>;
    let comp: MatchCommentaryClass;
    let matchCommentaryServiceStub: SinonStubbedInstance<MatchCommentaryService>;

    beforeEach(() => {
      matchCommentaryServiceStub = sinon.createStubInstance<MatchCommentaryService>(MatchCommentaryService);

      wrapper = shallowMount<MatchCommentaryClass>(MatchCommentaryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { matchCommentaryService: () => matchCommentaryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMatchCommentary = { id: 123 };
        matchCommentaryServiceStub.find.resolves(foundMatchCommentary);

        // WHEN
        comp.retrieveMatchCommentary(123);
        await comp.$nextTick();

        // THEN
        expect(comp.matchCommentary).toBe(foundMatchCommentary);
      });
    });
  });
});
