import React, {useState, Component, useRef} from "react";
import {Alert, Button, Card, Col, Container, Form, ListGroup, Row} from "react-bootstrap";

import BaseSelect from 'react-select'
import DatePicker from "react-datepicker";
import FixRequiredSelect from "./FixRequiredSelect";
import SweetAlert from 'react-bootstrap-sweetalert';

import { transitions, positions, useAlert, Provider as AlertProvider } from 'react-alert';
import AlertTemplate from 'react-alert-template-basic'
import PropTypes from "prop-types";


import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";
import {timeout} from "rxjs/operators";




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
function AlertDismissibleError() {
    const [show, setShow] = useState(true);

    if (show) {
        return (
            <Alert variant="danger" onClose={() => setShow(false)} dismissible>
                <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
                <p>
                    Nepodařilo se uložit.
                </p>
            </Alert>
        );
    }
    return <Button onClick={() => setShow(true)}>Show Alert</Button>;
}

function AlertDismissibleSuccess() {
    const [show, setShow] = useState(false);

    if (show) {
        return (
            <Alert variant="success" onClose={() => setShow(false)} dismissible>
                <Alert.Heading>Success</Alert.Heading>
                <p>
                    Podařilo se uložit.
                </p>
            </Alert>
        );
    }
    return <Button onClick={() => setShow(true)}>Show Alert</Button>;
}




class App extends React.Component {




    constructor(props) {
        super(props);

        this.state = {
            alert: null,
            errorState: false,
            successState: true,
            loading: true,
            data: [],
            dataTimes: [],
            error: null,
            errors: [],
            selectedValue: {label: 'Default value', key : '001'},
            persons:"",
			phoneValidationMessage: null,
			czPersonIdValidationMessage: null,
			emailValidationMessage: null,
			


            newReservationDate:new Date(),
			//newReservationDate:new Date(2021,4,5,20,0,0),
            dateSelect:"",
            newReservationTime:null,
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



        if(this.state.newReservationTime===null)

        { axios.get(APItimes+this.state.newReservationDate.toISOString().split("T")[0])
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
        this.state.newReservationTime="nezvoleny cas";
        }
		
	
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
	
	
    onChangeEmail = (e, name) => {
        //console.log("---", e, name)
        this.setState({
            [name]: e
        })
		
		if (!/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i.test(e))
			this.state.emailValidationMessage='Invalid email address';
		else
			this.state.emailValidationMessage='';
    }
	

    onChangePhoneNumber = (e, name) => {
        //console.log("---", e, name)
        this.setState({
            [name]: e
        })	
			
		if (!/^[+]?[()\0-9. -]{9,}$/i.test(e))
			this.state.phoneValidationMessage='Invalid phone number';
		else
			this.state.phoneValidationMessage='';
    }
	
	
	onChangePersonIdNumber = (e, name) => {
	    //console.log("---", e, name)
        this.setState({
            [name]: e
        })
				
		if(this.state.newNationality === 'cz' )
		{
			if (!/^[0-9]{6}\/?[0-9]{4}$/i.test(e)){
				this.state.czPersonIdValidationMessage= 'Incorrect czech Person ID pattern';
			}else{
		
				var number = e;
				 
				if ( /* contains */(number.indexOf("/") != -1)){
					number = /* replace */ number.split("/").join("");
				}
					
				if (number.length !== 10) {
					this.state.czPersonIdValidationMessage='Incorrect Lenght';
				}else{
					
					if ( /* parseInt */parseInt(number.substring(4, 6)) < 1 || /* parseInt */ parseInt(number.substring(4, 6)) > 31 || /* parseInt */ parseInt(number.substring(2, 4)) < 1 || /* parseInt */ parseInt(number.substring(2, 4)) > 62 || ( /* parseInt */parseInt(number.substring(2, 4)) > 12 && /* parseInt */ parseInt(number.substring(2, 4)) < 51) || (( /* parseInt */parseInt(number.substring(0, 9)) % 11) !== /* parseInt */ parseInt(number.substring(9, 10))))
					{
						this.state.czPersonIdValidationMessage= 'Incorrect';
					}else{
						this.state.czPersonIdValidationMessage= '';
					}
				}
			}
		}else{
			this.state.czPersonIdValidationMessage= '';
		}

    }


    onChangeDate = (e, name) => {

        //console.log("---", e, name)
        this.setState({
            [name]: e
        })
        this.state.newReservationTime=null
    }



    createNew = () => {
        const axios = require('axios').default;

        axios({
            method: 'post',
            url: 'http://localhost:8080/reservations',
            timeout: 15000,
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


                this.setState({
                    alert: (
                        <SweetAlert
                            success
                            confirmBtnText="OK"
                            confirmBtnBsStyle="primary"
                            title="Registrace byla zapsána"
                            timeout="60000"
                            onConfirm={() => {
                                this.setState({ alert: null });
								window.location.reload();
                            }}
                        >

                        </SweetAlert>
                    ),
                });



        }, (error) => {
            console.log(error);

                this.setState({
                    alert: (
                        <SweetAlert
                            danger
                            confirmBtnText="OK"
                            confirmBtnBsStyle="danger"
                            timeout="60000"
                            title="Nepodařilo se zapsat registraci."
                            onConfirm={() => {

                                this.setState({ alert: null });
                            }}
                        >

                        </SweetAlert>
                    ),
                });


        }).catch(error => {

            console.log(error);
            this.setState({
                alert: (

                    <SweetAlert
                        danger
                        confirmBtnText="OK"
                        confirmBtnBsStyle="danger"
                        timeout="60000"
                        title="Nepodařilo se zapsat registraci."
                        onConfirm={() => {

                            this.setState({ alert: null });
                        }}
                    >

                    </SweetAlert>
                ),
            });
        })
    }








    render() {




        return (

            <Container>
                <h1>Web app</h1>




                <Card>
                    <Card.Header></Card.Header>
                    <Card.Body>
                        <Card.Title>Vytvořte novou rezervaci</Card.Title>
                        <Form >
                            <Form.Row>
                            <Form.Group as={Col} controlId="firstNameId">
                                Zadejte jméno
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter name"
                                    value={this.state.newFirstName}
                                    onChange={(e) => this.onChange(e.target.value, 'newFirstName')}
                                />

                            </Form.Group>
                            <Form.Group as={Col} controlId="lastNameId">
                                Zadejte příjmení
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter surname"
                                    value={this.state.newLastName}
                                    onChange={(e) => this.onChange(e.target.value, 'newLastName')}
                                />
                            </Form.Group>
                                </Form.Row>
								<Form.Group controlId="nationalityId">
                                Vyberte národnost
                                <Select
                                    required
                                    options={optionsNationality}
                                    onChange={(e) => this.onChange(e.value, 'newNationality')}
                                />
                                </Form.Group>

                            <Form.Group controlId="personId">
                                Zadejte rodné číslo
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter personID"
                                    onChange={(e) => this.onChangePersonIdNumber(e.target.value, 'newPersonIdNumber')}
									//onChange={this.handleChange}
                                    value={this.state.newPersonIdNumber}
                                />
								<p style={{ color: 'red' }}>{this.state.czPersonIdValidationMessage}</p>
                            </Form.Group>
                            <Form.Group controlId="phoneId">
                                Zadejte telefon
                                <Form.Control
                                    required
                                    type="text"
                                    placeholder="Enter phone"
                                    onChange={(e) => this.onChangePhoneNumber(e.target.value, 'newPhone')}
                                    value={this.state.newPhone}
                                /><p style={{ color: 'red' }}>{this.state.phoneValidationMessage}</p>
                            </Form.Group>
                            <Form.Group controlId="emailId">
                                Zadejte email
                                <Form.Control
                                    required
                                    type="email"
                                    placeholder="Enter email"
                                    onChange={(e) => this.onChangeEmail(e.target.value, 'newEmail')}
                                    value={this.state.newEmail}
                                /><p style={{ color: 'red' }}>{this.state.emailValidationMessage}</p>
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
                         
                            <Form.Group controlId="reservationDateId">
                                Zvolte den rezervace, poté se zobrazí volné časy<br/>
                                <DatePicker
                                    selected={this.state.newReservationDate}
                                    onChange={(e) => this.onChangeDate((e), 'newReservationDate')}
                                />

                            </Form.Group>

                            <Form.Group controlId="reservationTimeId" >
                                Vyberte čas (pokud není čas k vybrání, vyberte jiný den)
                                <Select

                                    required
                                    placeholder={this.state.newReservationTime}

                                    options={this.state.dataTimes}
                                    onChange={(e) => this.onChange(e.value, 'newReservationTime')}
                                />

                            </Form.Group>

                            <Button variant="primary" 
							disabled={
								this.state.phoneValidationMessage !== '' || this.state.czPersonIdValidationMessage !== '' || 
								this.state.emailValidationMessage !== '' || 
								this.state.newReservationTime === "nezvoleny cas" || this.state.newFirstName.trim() === ''|| 
								this.state.newLastName.trim() === ''|| this.state.newPlateNumber.trim() === ''
							}
							onClick={this.createNew} type="button">Vytvořit</Button>
                            <Form.Group>
                                {this.state.alert}
                            </Form.Group>
                        </Form>
                    </Card.Body>
                </Card>




            </Container>



        );


    }
}

export default App;
