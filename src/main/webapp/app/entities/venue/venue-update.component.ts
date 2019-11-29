import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IVenue, Venue } from '@/shared/model/venue.model';
import VenueService from './venue.service';

const validations: any = {
  venue: {
    name: {
      required
    },
    description: {},
    address: {},
    phoneNumber: {},
    location: {}
  }
};

@Component({
  validations
})
export default class VenueUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('venueService') private venueService: () => VenueService;
  public venue: IVenue = new Venue();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.venueId) {
        vm.retrieveVenue(to.params.venueId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.venue.id) {
      this.venueService()
        .update(this.venue)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.venue.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.venueService()
        .create(this.venue)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.venue.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveVenue(venueId): void {
    this.venueService()
      .find(venueId)
      .then(res => {
        this.venue = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
