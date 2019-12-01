import { Component, Vue, Inject, Prop, Emit } from 'vue-property-decorator';
import { LOCATION } from '@/shared/model/location.model';
import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';

@Component
export default class StatsLocation extends Vue {

    public playerStats: IPlayerMatchStatistic;
    created () {
        console.log('created.....');
        this.index();
    }
    mounted () {
        console.log('mounted.....');
        this.$root.$on('setPlayerStatsLocation',(arg1) =>{
            this.setPlayerStatsLocation(arg1);
        });
    }

    public index(){
    
    }
    public onLocation(value: LOCATION){
        console.log(value);
        this.playerStats.location = value;
        
    }

    public setPlayerStatsLocation(item: IPlayerMatchStatistic) {
        console.log('emit set player loc');
        this.playerStats = item;
        console.log(this.playerStats);

    }

}