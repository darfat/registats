/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import VenueDetailComponent from '@/entities/venue/venue-details.vue';
import VenueClass from '@/entities/venue/venue-details.component';
import VenueService from '@/entities/venue/venue.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Venue Management Detail Component', () => {
    let wrapper: Wrapper<VenueClass>;
    let comp: VenueClass;
    let venueServiceStub: SinonStubbedInstance<VenueService>;

    beforeEach(() => {
      venueServiceStub = sinon.createStubInstance<VenueService>(VenueService);

      wrapper = shallowMount<VenueClass>(VenueDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { venueService: () => venueServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVenue = { id: 123 };
        venueServiceStub.find.resolves(foundVenue);

        // WHEN
        comp.retrieveVenue(123);
        await comp.$nextTick();

        // THEN
        expect(comp.venue).toBe(foundVenue);
      });
    });
  });
});
