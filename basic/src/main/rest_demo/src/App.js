import React, {useState, Component} from "react";
import {Button, Card, Col, Container, Form, ListGroup, Row} from "react-bootstrap";

import BaseSelect from 'react-select'
import DatePicker from "react-datepicker";
import FixRequiredSelect from "./FixRequiredSelect";
import validator from 'react-validation';
import PropTypes from "prop-types";

import Input from 'react-validation/build/input';
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";



const API='http://localhost:8080/reservations/localdate?localDate=';

const APItimes='http://localhost:8080/reservations/localdate/timesjson?localDate=';

const optionsNationality = [
    { value: 'cz', label: 'Čech' },
    { value: '--', label: 'Cizinec' }
    ]

const Select = props => (
    <FixRequiredSelect
        {...props}
        SelectComponent={BaseSelect}

    />
);



class App extends React.Component {



    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            data: [],
            dataTimes: [],
            error: null,






            newReservationDate:new Date(2021,4,5,20,0,0),

            newReservationTime:"",
            newFirstName: "",
            newLastName: "",
            newPersonIdNumber: "",
            newPhone: "",
            newEmail: "",
            newPlateNumber: "",
            newNote: "",
            newNationality: "",
            reservationId: "",



        };
    }





    componentDidUpdate() {
        const axios = require('axios').default;





        axios.get(APItimes+this.state.newReservationDate.toISOString().split("T")[0])
            .then((response) => {
                this.setState({
                    'dataTimes': response.data,


                })
                //console.log(response.data);
                //console.log(response.status);
                //console.log(response.statusText);
                //console.log(response.headers);
                //console.log(response.config);
            });


    }









    async componentDidMount() {


        const axios = require('axios').default;


        axios.get(API+this.state.newReservationDate.toISOString().split("T")[0])
            .then((response) => {
                this.setState({
                    'data': response.data
                })
                //console.log(response.data);
                //console.log(response.status);
                //console.log(response.statusText);
                //console.log(response.headers);
                //console.log(response.config);
            });

        axios.get(APItimes+this.state.newReservationDate.toISOString().split("T")[0])
            .then((response) => {
                this.setState({
                    'dataTimes': response.data
                })
                //console.log(response.data);
                //console.log(response.status);
                //console.log(response.statusText);
                //console.log(response.headers);
                //console.log(response.config);
            });


    }







    onChange = (e, name) => {


        //console.log("---", e, name)
        this.setState({
            [name]: e
        })
    }













    createNew = () => {
        const axios = require('axios').default;

        axios({
            method: 'post',
            url: 'http://localhost:8080/reservations',
            data: {
                "reservationDate":  this.state.newReservationDate.toISOString(),
                "reservationTime": this.state.newReservationTime,
                "firstName": this.state.newFirstName,
                "lastName": this.state.newLastName,
                "plateNumber": this.state.newPlateNumber,
                "personIdNumber": this.state.newPersonIdNumber,
                "phone": this.state.newPhone,
                "email": this.state.newEmail,
                "note": this.state.newNote,
                "nationality": this.state.newNationality
            }
        }).then((response) => {
            console.log(response);
        }, (error) => {
            console.log(error);
        });
    }

    updateExisting = () => {
        const axios = require('axios').default;
        axios({
            method: 'put',
            url: 'http://localhost:8080/reservations/' + this.state.reservationId,
            data: {


                "firstName": this.state.updateFirstName,
                "lastName": this.state.updateLastName,
                "plateNumber": this.state.updatePlateNumber,
                "personIdNumber": this.state.updatePersonIdNumber,
                "phone": this.state.updatePhone,
                "email": this.state.updateEmail,
                "note": this.state.updateNote,
                "nationality": this.state.updateNationality
            }
        }).then((response) => {
            console.log(response);
        }, (error) => {
            console.log(error);
        });

    }


    render() {




        return (
            <Container>
                <h1>Web app</h1>




                <Card>
                    <Card.Header></Card.Header>
                    <Card.Body>
                        <Card.Title>Vytvořte novou rezervaci</Card.Title>
                        <Form>
                            <Form.Group controlId="firstNameId">
                                Zadejte jméno
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter name"
                                    value={this.state.newFirstName}
                                    onChange={(e) => this.onChange(e.target.value, 'newFirstName')}
                                />

                            </Form.Group>
                            <Form.Group controlId="lastNameId">
                                Zadejte příjmení
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter surname"
                                    value={this.state.newLastName}
                                    onChange={(e) => this.onChange(e.target.value, 'newLastName')}
                                />
                            </Form.Group>

                            <Form.Group controlId="personId">
                                Zadejte rodné číslo
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter personID"
                                    onChange={(e) => this.onChange(e.target.value, 'newPersonIdNumber')}
                                    value={this.state.newPersonIdNumber}
                                />
                            </Form.Group>
                            <Form.Group controlId="phoneId">
                                Zadejte telefon
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter phone"
                                    onChange={(e) => this.onChange(e.target.value, 'newPhone')}
                                    value={this.state.newPhone}
                                />
                            </Form.Group>
                            <Form.Group controlId="emailId">
                                Zadejte email
                                <Form.Control
                                    required
                                    type="email"
                                    placeholder="Enter email"
                                    onChange={(e) => this.onChange(e.target.value, 'newEmail')}
                                    value={this.state.newEmail}
                                />
                            </Form.Group>
                            <Form.Group controlId="plateNumberId">
                                Zadejte registrační značku
                            <Form.Control
                                required
                                type="text"
                                placeholder="Enter plate number"
                                    onChange={(e) => this.onChange(e.target.value, 'newPlateNumber')}
                                    value={this.state.newPlateNumber}
                                />
                            </Form.Group>
                            <Form.Group controlId="noteId">
                                Zadejte popis poruchy
                                <Form.Control
                                    type="text"
                                    placeholder="Enter note"
                                    onChange={(e) => this.onChange(e.target.value, 'newNote')}
                                    value={this.state.newNote}
                                />
                            </Form.Group>
                            <Form.Group controlId="nationalityId">
                                Vyberte národnost
                                <Select
                                    required
                                    name="form-field-name"
                                    options={optionsNationality}
                                    onChange={(e) => this.onChange(e.value, 'newNationality')}
                                />

                                </Form.Group>

                            <Form.Group controlId="reservationDateId">
                                Zvolte den rezervace<br/>
                                <DatePicker
                                    selected={this.state.newReservationDate}
                                    onChange={(e) => this.onChange((e), 'newReservationDate')}
                                />

                            </Form.Group>

                            <Form.Group controlId="reservationTimeId" >
                                Vyberte čas (pokud není čas k vybrání, vyberte jiný den)
                                <Select
                                    required
                                    name="form-field-name"
                                    options={this.state.dataTimes}
                                    onChange={(e) => this.onChange(e.value, 'newReservationTime')}
                                />

                            </Form.Group>

                            <Button variant="primary" onClick={this.createNew} type="submit">Create</Button>
                        </Form>
                    </Card.Body>
                </Card>


            </Container>
        );
    }
}

export default App;
