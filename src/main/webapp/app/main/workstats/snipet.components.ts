import { Component, Vue, Inject, Prop } from 'vue-property-decorator';

@Component
export default class Snipet extends Vue {

    created () {
        console.log('created.....');
        this.index();
    }
    mounted () {
        console.log('mounted.....');
    }

    public index(){
    
    }
}