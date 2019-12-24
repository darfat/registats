import { Component, Vue, Inject, Prop, Emit } from 'vue-property-decorator';
import { LOCATION } from '@/shared/model/location.model';
import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';
import { IPlayerMatchStatisticLocation } from '@/shared/model/player-match-statistic-location.model';

@Component
export default class StatsLocation extends Vue {

    public statIdx : number = -1;
    public playerStats: IPlayerMatchStatistic;
    created () {
        console.log('created.....');
        this.index();
    }
    mounted () {
        console.log('mounted.....');
        this.$root.$on('setPlayerStatsLocation',(arg1,index) =>{
            this.setPlayerStatsLocation(arg1,index);
        });
    }

    public index(){
    
    }
    public onLocation(value: string){
        if(this.playerStats) {
            console.log(this.playerStats.matchLineup.player.fullName);
            console.log(this.playerStats.statistic.name);
            console.log(value);
            let location  :IPlayerMatchStatisticLocation = {};
            location.location = value;
            // this.playerStats.locations.push(location);
            if (this.statIdx >= 0){
                this.$root.$emit('addStatsLocation',location,this.statIdx);
            }
            this.statIdx = -1;
            
        }
    }

    public setPlayerStatsLocation(item: IPlayerMatchStatistic,idx) {
        console.log('location for : ');
        this.playerStats = item;
        this.statIdx = idx;

    }

}