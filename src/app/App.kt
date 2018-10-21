package app

import components.header
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onKeyPressFunction
import react.*
import react.dom.*
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.Response
import kotlin.browser.window

interface SearchBarState : RState {
    var githubUserName: String
    var repoList: Array<Repo>
}

external interface Repo {
    var name: String
    var html_url: String
}

class App : RComponent<RProps, SearchBarState>() {

    override fun SearchBarState.init() {
        githubUserName = ""
    }

    override fun RBuilder.render() {

        div ("container") {
                    header()
                    br {  }

                    div (classes = "row") {
                        div (classes = "col-md-12") {

                            div(classes = "form-inline") {
                                div ("form-group mx-sm-2") {
                                    input(InputType.search, classes = "form-control") {
                                        attrs {
                                            value = state.githubUserName
                                            onChangeFunction = ::onInputChange
                                            onKeyPressFunction = ::handleKeyPress
                                            placeholder = "Enter Github User Name"
                                        }
                                    }
                                }

                                button (classes = "btn btn-outline-dark" ) {
                                    attrs {
                                        onClickFunction = ::searchByUser
                                    }
                                    +"Search"
                                }
                            }

                            hr {  }

                            ul (classes = "list-group") {
                                state.repoList?.map { item ->
                                    li (classes = "list-group-item gc-list") {
                                        +"${ item.name }"
                                        div {
                                            +"${ item.html_url }"
                                        }
                                    }
                                }
                            }
                        }
                    }
        }
    }

    fun handleKeyPress (event: Event) {
        if(13 == js("event.which")) {
            searchByUser(event)
        }
    }

    fun searchByUser (event: Event) {

        window.fetch ("https://api.github.com/users/${state.githubUserName}/repos")
            .then (Response::json)
            .then ( { repos: Any ->
                    setState {
                        state.repoList = repos as Array<Repo>
                    }
            } )
    }

    fun onInputChange(event: Event) {

        val target = event.target as HTMLInputElement
        val searchTerm = target.value
        setState {
            githubUserName = searchTerm
        }

    }
}

fun RBuilder.app() = child(App::class) {}
