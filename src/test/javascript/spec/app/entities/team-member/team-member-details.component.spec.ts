/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TeamMemberDetailComponent from '@/entities/team-member/team-member-details.vue';
import TeamMemberClass from '@/entities/team-member/team-member-details.component';
import TeamMemberService from '@/entities/team-member/team-member.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TeamMember Management Detail Component', () => {
    let wrapper: Wrapper<TeamMemberClass>;
    let comp: TeamMemberClass;
    let teamMemberServiceStub: SinonStubbedInstance<TeamMemberService>;

    beforeEach(() => {
      teamMemberServiceStub = sinon.createStubInstance<TeamMemberService>(TeamMemberService);

      wrapper = shallowMount<TeamMemberClass>(TeamMemberDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { teamMemberService: () => teamMemberServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeamMember = { id: 123 };
        teamMemberServiceStub.find.resolves(foundTeamMember);

        // WHEN
        comp.retrieveTeamMember(123);
        await comp.$nextTick();

        // THEN
        expect(comp.teamMember).toBe(foundTeamMember);
      });
    });
  });
});
