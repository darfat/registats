import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitionTeamStatistic } from '@/shared/model/competition-team-statistic.model';
import CompetitionTeamStatisticService from './competition-team-statistic.service';

@Component
export default class CompetitionTeamStatisticDetails extends Vue {
  @Inject('competitionTeamStatisticService') private competitionTeamStatisticService: () => CompetitionTeamStatisticService;
  public competitionTeamStatistic: ICompetitionTeamStatistic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionTeamStatisticId) {
        vm.retrieveCompetitionTeamStatistic(to.params.competitionTeamStatisticId);
      }
    });
  }

  public retrieveCompetitionTeamStatistic(competitionTeamStatisticId) {
    this.competitionTeamStatisticService()
      .find(competitionTeamStatisticId)
      .then(res => {
        this.competitionTeamStatistic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
