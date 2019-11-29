/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompetitionNameDetailComponent from '@/entities/competition-name/competition-name-details.vue';
import CompetitionNameClass from '@/entities/competition-name/competition-name-details.component';
import CompetitionNameService from '@/entities/competition-name/competition-name.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompetitionName Management Detail Component', () => {
    let wrapper: Wrapper<CompetitionNameClass>;
    let comp: CompetitionNameClass;
    let competitionNameServiceStub: SinonStubbedInstance<CompetitionNameService>;

    beforeEach(() => {
      competitionNameServiceStub = sinon.createStubInstance<CompetitionNameService>(CompetitionNameService);

      wrapper = shallowMount<CompetitionNameClass>(CompetitionNameDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { competitionNameService: () => competitionNameServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompetitionName = { id: 123 };
        competitionNameServiceStub.find.resolves(foundCompetitionName);

        // WHEN
        comp.retrieveCompetitionName(123);
        await comp.$nextTick();

        // THEN
        expect(comp.competitionName).toBe(foundCompetitionName);
      });
    });
  });
});
