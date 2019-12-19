import { Component, Vue, Inject, Prop } from 'vue-property-decorator';
import { IMatchTeamInfo } from '@/shared/model/match-team-info.model';
import { IMatchStatisticItem, MatchStatisticItem } from '@/shared/model/match-statistic-item.model';
import MatchStatisticItemService from '@/entities/match-statistic-item/match-statistic-item.service';

@Component
export default class StatsTeam extends Vue {
    @Prop(Object) team: IMatchTeamInfo | undefined
    @Prop(String) readonly label: string | undefined
    @Prop(String) readonly name: string | undefined

    @Inject('matchStatisticItemService') private matchStatisticItemService: () => MatchStatisticItemService;

    public teamStatisticItems = [];
    public item: IMatchStatisticItem = null;

    created () {
        console.log('created.....');        
    }
    mounted () {
        console.log('mounted.....');
        this.index();
    }

    public index(){
        this.loadReferences();
        console.log('thiss team....')
        console.log(this.team);
    }

    public add(){
        this.addStatistics(this.item);
    }

    public addStatistics(e: MatchStatisticItem){    
        console.log(e);    
        const data = this.team.statistics.slice();
        const index = data.findIndex(s => s.statistic.id === e.id);
        if( index !== -1 ){
            data[index].value = data[index].value +1;
            console.log(e.name+' : ' + data[index].value);
            this.team.statistics[index] = data[index];
            //this.$root.$emit('addMatchCommentary',this.player.player,e);
           // this.$root.$emit('updateTeamLineup');
        } else {
            console.error('couldnt add stats');
        }
    }

    public loadReferences(){
        this.matchStatisticItemService()
            .retrieve()
            .then(res => {
            this.teamStatisticItems = res.data;
            this.item = this.findStatsItemByName(this.name);
        });
    }

    public findStatsItemByName(name: string) : IMatchStatisticItem{
        const result = this.teamStatisticItems.find( item => item.name === name );
        return result;
    }
}