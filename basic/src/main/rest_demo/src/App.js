import React, {useState, Component} from "react";
import {Button, Card, Col, Container, Form, ListGroup, Row} from "react-bootstrap";
import Select from 'react-select'
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";




class App extends React.Component {



    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            data: [],
            dataTimes: [],
            error: null,


            selectedDate: new Date(2021,5,3),
            selectedDateString: ""+new Date(2021,5,3).toString(),
            newReservationDate:new Date(2021,3,28),
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


            updateFirstName: "",
            updateLastName: "",
            updatePersonIdNumber: "",
            updatePhone: "",
            updateEmail: "",
            updatePlateNumber: "",
            updateNote: "",
            updateNationality: "",
        };
    }









    async componentDidMount() {
        const axios = require('axios').default;


        axios.get('http://localhost:8080/reservations/localdate?localDate=2021-05-03')
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
    }

   /* async componentDidMountTime() {
        const axios = require('axios').default;

        axios.get('http://localhost:8080/reservations/localdate/times?localDate='+this.selectedDate)
            .then((response) => {
                this.setState({
                    'dataTimes': response.dataTimes
                })
                //console.log(response.data);
                //console.log(response.status);
                //console.log(response.statusText);
                //console.log(response.headers);
                //console.log(response.config);
            });
    }*/





    onChange = (e, name) => {
        //console.log("---", e, name)
        this.setState({
            [name]: e
        })
    }

    createSelect = () => { const options = [
        { value: 'chocolate', label: 'Chocolate' },
        { value: 'strawberry', label: 'Strawberry' },
        { value: 'vanilla', label: 'Vanilla' }
    ]

    const MyComponent = () => (
        <Select options={options} />
    )}






    createNew = () => {
        const axios = require('axios').default;

        axios({
            method: 'post',
            url: 'http://localhost:8080/reservations',
            data: {
                "reservationDate":  this.state.newReservationDate,
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

        const DisplayItems = (data) => {
            const list = data.children.map(item => <CustomItem key={item.id}>{item}</CustomItem>)
            return (
                <ListGroup variant="flush">
                    {list}
                </ListGroup>
            )
        }

        const CustomItem = (data) => {
            const item = data.children;
            return <ListGroup.Item>
                <Col>
                    <Row><b> id:</b> {item.id} </Row>
                    <Row><b> firstName:</b> {item.firstName} </Row>
                    <Row><b> lastName:</b> {item.lastName} </Row>
                    <Row><b> plateNumber:</b> {item.plateNumber} </Row>
                    <Row><b> reservationTime:</b> {item.reservationTime} </Row>
                    <Row><b> personIdNumber:</b> {item.personIdNumber} </Row>
                    <Row><b> phone:</b> {item.phone} </Row>
                    <Row><b> email:</b> {item.email} </Row>
                    <Row><b> note:</b> {item.note} </Row>
                    <Row><b> nationality:</b> {item.nationality} </Row>
                </Col>
            </ListGroup.Item>;
        };

        return (
            <Container>
                <h1>Web app</h1>
                <Card>
                    <DisplayItems>{this.state.data}</DisplayItems>
                </Card>

                <Card>
                    <Card.Header>Create</Card.Header>
                    <Card.Body>
                        <Card.Title>Create new reservation</Card.Title>
                        <Form>
                            <Form.Group controlId="firstNameId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter name"
                                    value={this.state.newFirstName}
                                    onChange={(e) => this.onChange(e.target.value, 'newFirstName')}
                                />
                            </Form.Group>
                            <Form.Group controlId="lastNameId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter surname"
                                    value={this.state.newLastName}
                                    onChange={(e) => this.onChange(e.target.value, 'newLastName')}
                                />
                            </Form.Group>










                            <Form.Group controlId="personId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter personID"
                                    onChange={(e) => this.onChange(e.target.value, 'newPersonIdNumber')}
                                    value={this.state.newPersonIdNumber}
                                />
                            </Form.Group>
                            <Form.Group controlId="phoneId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter phoneID"
                                    onChange={(e) => this.onChange(e.target.value, 'newPhone')}
                                    value={this.state.newPhone}
                                />
                            </Form.Group>
                            <Form.Group controlId="emailId">
                                <Form.Control
                                    type="email"
                                    placeholder="Enter email"
                                    onChange={(e) => this.onChange(e.target.value, 'newEmail')}
                                    value={this.state.newEmail}
                                />
                            </Form.Group>
                            <Form.Group controlId="plateNumberId">
                            <Form.Control
                                type="text"
                                placeholder="Enter plate number"
                                    onChange={(e) => this.onChange(e.target.value, 'newPlateNumber')}
                                    value={this.state.newPlateNumber}
                                />
                            </Form.Group>
                            <Form.Group controlId="plateNoteId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter note"
                                    onChange={(e) => this.onChange(e.target.value, 'newNote')}
                                    value={this.state.newNote}
                                />
                            </Form.Group>
                            <Form.Group controlId="nationalityId">
                            <Form.Control
                                as="select"
                                custom
                                onChange={(e) => this.onChange(e.target.value, 'newNationality')}
                            >
                                <option value="Zvolte možnost">Zvolte možnost</option>
                                <option value="cz">Čech</option>
                                <option value="--">Cizinec</option>

                            </Form.Control>
                                </Form.Group>

                            <Form.Group controlId="reservationTimeId">



                                <Form.Control
                                    as="select"
                                    custom
                                    onChange={(e) => this.onChange(e.target.value, 'newReservationTime')}
                                >
                                    <option value="Zvolte možnost">Zvolte možnost</option>
                                    <option value="11:00:00">11:00:00</option>
                                    <option value="12:00:00">12:00:00</option>
                                    <option value="13:00:00">13:00:00</option>
                                    <option value="14:00:00">14:00:00</option>
                                    <option value="15:00:00">15:00:00</option>

                                </Form.Control>

                            </Form.Group>

                            <Form.Group controlId="reservationDateId">


                                <DatePicker


                                    selected={this.state.newReservationDate}
                                    onChange={(e) => this.onChange(e, 'newReservationDate')}
                                    value={this.state.newReservationDate}

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
