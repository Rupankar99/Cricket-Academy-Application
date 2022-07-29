import React from 'react'
import UserNavbar from './UserNavbar';
import { useState, useEffect } from 'react';
import User_Service from '../../services/User_Service';
import auth_id from '../../services/auth_id';
import { Card } from 'react-bootstrap'
const EnrolledCourse = () => {

    const [enrolledcourses, setEnrolledCourses] = useState([]);

    const listEnrolledCourses = () => {
        User_Service.getEnrolledCourses(auth_id())
            .then((response) => {
                console.log(response.data);
                setEnrolledCourses(response.data)
            })
            .catch((e) => {
                console.log(e)
            })
    }


    useEffect(() => {
        listEnrolledCourses()
    }, [])



    return (
        <>
            <UserNavbar />
            <div className='d-flex'>
                {
                    enrolledcourses.map(enrolledcourse => (
                        <Card style={{ width: '12rem', marginLeft: "2rem", marginTop: "2rem", padding: "1rem" }}>
                            <Card.Title>{enrolledcourse.courses[0].courseName}</Card.Title>
                            <Card.Text style={{ color: 'Black', fontWeight: "bolder" }}>
                                Course Duration : {enrolledcourse.courses[0].courseDuration}
                            </Card.Text>
                            <Card.Text style={{ color: 'Black', fontWeight: "bolder" }}>
                                Course Description : {enrolledcourse.courses[0].courseDescription}
                            </Card.Text>
                        </Card>
                    ))
                }
            </div>
        </>
    )
}

export default EnrolledCourse