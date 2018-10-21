package components

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.nav
import react.dom.span

class Header: RComponent<RProps, RState>() {
    override fun RBuilder.render () {
        div ("row") {
            div("col-md-12") {
                nav("navbar navbar-expand-lg navbar-dark bg-dark") {
                    span("navbar-brand") {
                        +"Github Client"
                    }
                }
            }
        }
    }
}

fun RBuilder.header() = child(Header::class) {}

