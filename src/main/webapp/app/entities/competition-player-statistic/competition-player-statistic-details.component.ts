import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitionPlayerStatistic } from '@/shared/model/competition-player-statistic.model';
import CompetitionPlayerStatisticService from './competition-player-statistic.service';

@Component
export default class CompetitionPlayerStatisticDetails extends Vue {
  @Inject('competitionPlayerStatisticService') private competitionPlayerStatisticService: () => CompetitionPlayerStatisticService;
  public competitionPlayerStatistic: ICompetitionPlayerStatistic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionPlayerStatisticId) {
        vm.retrieveCompetitionPlayerStatistic(to.params.competitionPlayerStatisticId);
      }
    });
  }

  public retrieveCompetitionPlayerStatistic(competitionPlayerStatisticId) {
    this.competitionPlayerStatisticService()
      .find(competitionPlayerStatisticId)
      .then(res => {
        this.competitionPlayerStatistic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
