import React, { ChangeEvent } from "react"
import { updateData } from "./app"

interface FormState {
    fields: {
        login: string
        pass: string
        res: string
        role: string
        ds: string
        de: string
        vol: string
    }
}

export default class Form extends React.Component<{}, FormState> {
    constructor(props: object) {
        super(props)
        this.state = {
            fields: {
                login: "",
                pass: "",
                res: "",
                role: "",
                ds: "",
                de: "",
                vol: ""
            }
        }
        this.changeHandler = this.changeHandler.bind(this)
        this.send = this.send.bind(this)
    }

    changeHandler(event: ChangeEvent<HTMLInputElement>) {
        // @ts-ignore
        this.state.fields[event.target.getAttribute("name")] = event.target.value
    }

    send() {
        let req = new Request("ajax/activity", { method: "POST", body: JSON.stringify(this.state.fields) })
        fetch(req).then(response => response.text()).then(msg => {
            if (msg == "0") {
                let table = document.getElementById("activity_table")
                try {
                    updateData("activity", table.getAttribute("query"))
                } catch(e) {}
            }
        })
    }

    renderFields() {
        let fields = Object.keys(this.state.fields)
        return fields.map((value) => {
            return <div id="input_container">{value}<input id="input_field" type="text" name={value} onChange={this.changeHandler}></input></div>
        })
    }

    render() {
        return (
            <form>{this.renderFields()}<input type="button" id="submit-button" value="Submit" onClick={this.send} ></input></form>
        );
    }

}
