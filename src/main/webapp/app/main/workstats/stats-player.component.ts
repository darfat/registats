import { Component, Vue, Inject, Prop } from 'vue-property-decorator';
import { IPlayer } from '@/shared/model/player.model';
import { IPlayerStatisticItem, PlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';
import { IMatchLineup } from '@/shared/model/match-lineup.model';

@Component
export default class StatsPlayer extends Vue {
    @Prop(Object) player: IMatchLineup | undefined
    @Prop(String) readonly label: string | undefined
    @Prop(String) readonly labelSuccess: string | undefined
    @Prop(String) readonly labelFailed: string | undefined
    @Prop(String) readonly nameSuccess: string | undefined
    @Prop(String) readonly nameFailed: string | undefined

    @Inject('playerStatisticItemService') private playerStatisticItemService: () => PlayerStatisticItemService;

    public playerStatisticItems = [];
    public itemSuccess: IPlayerStatisticItem = null;
    public itemFailed: IPlayerStatisticItem = null;

    created () {
        this.index();
        this.$root.$on('syncLineup',(updatedLineup) =>{
            this.syncLineup(updatedLineup);
        });
    }
    mounted () {      
    }

    public index(){
        this.loadReferences();
    }

    public success(){
        this.addStatistics(this.itemSuccess);
    }
    public missed(){
        this.addStatistics(this.itemFailed);
    }


    // get reference
    public loadReferences(){
        this.playerStatisticItemService()
            .retrieve()
            .then(res => {
            this.playerStatisticItems = res.data;
            this.itemSuccess = this.findStatsItemByName(this.nameSuccess);
            this.itemFailed = this.findStatsItemByName(this.nameFailed);
        });
    }

    public findStatsItemByName(name: string) : IPlayerStatisticItem{
        const result = this.playerStatisticItems.find( item => item.name === name );
        return result;
    }

    public addStatistics(e: PlayerStatisticItem){        
        const data = this.player.statistics.slice();
        const index = data.findIndex(s => s.statistic.id === e.id);
        if( index !== -1 ){
            data[index].value = data[index].value +1;
            console.log(e.name+' : ' + data[index].value);
            this.player.statistics[index] = data[index];
            //   this.$root.$emit('setPlayerStatsLocation',this.player.statistics[index]);
            this.$root.$emit('addMatchCommentary',this.player.player,e);
            //this.$root.$emit('updateTeamLineup');        
        } else {
            console.error('couldnt add stats');
        }
    }

    public syncLineup(updatedLineup: IMatchLineup): any {
        console.log('updated lineup....')
        if(updatedLineup !== null){
          this.player = updatedLineup;
        }    
      }
}