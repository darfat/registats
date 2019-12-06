import { Component, Vue,  Prop } from 'vue-property-decorator';
import { PositionEnum } from '../model/position.model';

@Component
export default class DropdownCell extends Vue {
    @Prop(String) readonly field: string | undefined
    @Prop(Object) readonly dataItem: any | undefined
    @Prop(String) readonly format: string | undefined
    @Prop(String) readonly className: string | undefined
    @Prop(Number) readonly columnIndex: number | undefined
    @Prop(Number) readonly columnsCount: number | undefined
    @Prop(String) readonly rowType: string | undefined
    @Prop(Number) readonly level: number | undefined
    @Prop(Boolean) readonly expanded: boolean | undefined
    @Prop(String) readonly editor: string | undefined

    public options: string[] = [];

    mounted () {
        let i:number = 0;
        for (let item in PositionEnum) {
            this.options.push(item);
        }
        
    }

    change(e) {
        this.$emit('change', e, e.target.value);
    }
}