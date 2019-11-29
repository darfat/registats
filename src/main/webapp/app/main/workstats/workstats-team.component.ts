import { Component, Vue, Inject, Prop } from 'vue-property-decorator';
import { Team, ITeam } from '@/shared/model/team.model';
import { ImatchTeamInfo } from '@/shared/model/match-team-info.model';

@Component
export default class WorkstatsTeam extends Vue {
  @Prop(Object) readonly team: ITeam | undefined
  @Prop(Object) teamInfo: ImatchTeamInfo | undefined

  beforeRouteEnter(to, from, next) {
    next(vm => {
    });
  }

  mounted () {
    this.index()
  }

  public index(){
    console.log(this.team.name);
    console.log(this.teamInfo);
  }

  public previousState() {
    this.$router.go(-1);
  }
}
