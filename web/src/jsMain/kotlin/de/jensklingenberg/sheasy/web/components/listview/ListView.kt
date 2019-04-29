package de.jensklingenberg.sheasy.web.components.listview

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.model.render
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

interface ListViewProps : RProps {
    var itemsListp: List<SourceItem>

}

interface ListViewState : RState {
    var itemsList: List<SourceItem>

}


class ListView(props: ListViewProps) : RComponent<ListViewProps, ListViewState>(props) {

    override fun componentDidMount() {

    }

    override fun ListViewState.init(props: ListViewProps) {

        itemsList = props.itemsListp//listOf(StringSourceItem("HHUU"))

    }

    override fun RBuilder.render() {
       state.itemsList.render(this)

    }



}



fun RBuilder.listView(itemsList2: List<SourceItem>) = child(ListView::class) {
    this.attrs.itemsListp = itemsList2
}