import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVenue } from '@/shared/model/venue.model';
import VenueService from './venue.service';

@Component
export default class VenueDetails extends Vue {
  @Inject('venueService') private venueService: () => VenueService;
  public venue: IVenue = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.venueId) {
        vm.retrieveVenue(to.params.venueId);
      }
    });
  }

  public retrieveVenue(venueId) {
    this.venueService()
      .find(venueId)
      .then(res => {
        this.venue = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
