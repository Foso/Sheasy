package components

import org.w3c.dom.events.Event
import react.RClass
import react.RProps


@JsModule("react-autosuggest")
@JsNonModule
@JsName("default")
external val autosuggest: RClass<AutosuggestProps>


class TSuggestion : ArrayList<String>() {

}


interface SuggestionsFetchRequestedParams {
    var value: String
    var reason: dynamic /* String /* "input-changed" */ | String /* "input-focused" */ | String /* "escape-pressed" */ | String /* "suggestions-revealed" */ | String /* "suggestion-selected" */ */
}

interface RenderSuggestionParams {
    var query: String
    var isHighlighted: Boolean
}

interface SuggestionHighlightedParams {
    var suggestion: Any
}

interface ChangeEvent {
    var newValue: String
    var method: dynamic /* String /* "down" */ | String /* "up" */ | String /* "escape" */ | String /* "enter" */ | String /* "click" */ | String /* "type" */ */
}

interface BlurEvent<TSuggestion> {
    var highlightedSuggestion: TSuggestion
}

external interface InputProps {
    var onChange: (Event) -> Unit
    val onBlur: ((event: (Event) -> Unit, params: (Event) -> Unit /*= null*/) -> Unit)? get() = definedExternally
    var value: String
}

interface SuggestionSelectedEventData<TSuggestion> {
    var suggestion: TSuggestion
    var suggestionValue: String
    var suggestionIndex: Number
    var sectionIndex: Number?
    var method: dynamic /* String /* "enter" */ | String /* "click" */ */
}

interface `T$0` {
    var id: String
    var key: String
    var ref: Any
    var style: Any
}

external interface RenderSuggestionsContainerParams {
    var containerProps: `T$0`
    var children: dynamic get() = definedExternally; set(value) = definedExternally
    var query: String
}

external interface AutosuggestProps : RProps {

    var alwaysRenderSuggestions: Boolean? get() = definedExternally; set(value) = definedExternally
    var focusInputOnSuggestionClick: Boolean? get() = definedExternally; set(value) = definedExternally
    var getSectionSuggestions: ((section: Any) -> Array<TSuggestion>)? get() = definedExternally; set(value) = definedExternally
    var getSuggestionValue: (suggestion: Array<String>) -> String
    var highlightFirstSuggestion: Boolean? get() = definedExternally; set(value) = definedExternally
    var id: String? get() = definedExternally; set(value) = definedExternally
    var inputProps: InputProps
    var multiSection: Boolean? get() = definedExternally; set(value) = definedExternally
    var onSuggestionHighlighted: ((params: SuggestionHighlightedParams) -> Unit)? get() = definedExternally; set(value) = definedExternally
    var onSuggestionsFetchRequested: (request: SuggestionsFetchRequestedParams) -> Unit
    var onSuggestionsClearRequested: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
    var onSuggestionSelected: ((event: (Event) -> Unit, data: SuggestionSelectedEventData<TSuggestion>) -> Unit)? get() = definedExternally; set(value) = definedExternally
    var renderInputComponent: ((inputProps: InputProps) -> dynamic)? get() = definedExternally; set(value) = definedExternally
    var renderSuggestionsContainer: ((params: RenderSuggestionsContainerParams) -> dynamic)? get() = definedExternally; set(value) = definedExternally
    var renderSectionTitle: ((section: Any) -> dynamic)? get() = definedExternally; set(value) = definedExternally
    var renderSuggestion: (suggestion: TSuggestion, params: RenderSuggestionParams) -> dynamic
    var shouldRenderSuggestions: ((value: String) -> Boolean)? get() = definedExternally; set(value) = definedExternally
    var suggestions: Array<String>
    var theme: Any? get() = definedExternally; set(value) = definedExternally
}
