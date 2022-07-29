import React, { useState, useEffect } from 'react'
import AdminNavbar from './AdminNavbar'
import Course_Service from '../../services/Course_Service'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTrash, faPenToSquare, faCaretDown, faCaretUp } from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router-dom'
import Paginate from '../Pages/Paginate'

const ViewAllCourses = () => {

    const [courses, setCourses] = useState([])
    const [dataSize, setDataSize] = useState(null)
    const [pageNumber, setPageNumber] = useState(0)
    const [sortBy, setSortBy] = useState("courseName")
    const [sortDir, setSortDir] = useState("asc")
    const [flag, setFlag] = useState(true)

    const navigate = useNavigate()

    const pageSize = 3

    const fetchData = () => {
        Course_Service.getAllCourses(pageNumber, pageSize, sortBy, sortDir)
            .then((response) => {
                setCourses(response.data)
                Course_Service.countCourses()
                    .then((res) => {
                        setDataSize(res.data)
                    })
                    .catch((err) => {
                        console.log(err)
                    })
            })
            .catch((error) => console.log(error))
    }

    useEffect(() => {
        fetchData()
    }, [pageNumber, sortBy, sortDir])

    const paginate = (pagenum) => {
        setPageNumber(pagenum)
    }

    const sortFieldChange = (field) => {
        setSortBy(field)
        if (sortDir === "asc") {
            setSortDir("desc")
            setFlag(false)
        }
        else if (sortDir === "desc") {
            setSortDir("asc")
            setFlag(true)
        }
    }

    const showPrevPage = () => {
        if (pageNumber > 0) {
            setPageNumber(pagenum => pagenum - 1)
        }
    }

    const showNextPage = () => {
        if (pageNumber < Math.ceil(dataSize / pageSize) - 1) {
            setPageNumber(pagenum => pagenum + 1)
        }
    }

    return (
        <>
            <AdminNavbar />
            <table className="table table-striped mx-auto bg-light w-75 mt-4">
                <thead className='bg-dark text-white'>
                    <tr>
                        <th> Course Name <button className='btn btn-sm btn-dark' onClick={() => sortFieldChange("courseName")}><FontAwesomeIcon icon={flag ? faCaretDown : faCaretUp} /></button></th>
                        <th> Course Duration <button className='btn btn-sm btn-dark' onClick={() => sortFieldChange("courseDuration")}><FontAwesomeIcon icon={flag ? faCaretDown : faCaretUp} /></button></th>
                        <th> Course Description <button className='btn btn-sm btn-dark' onClick={() => sortFieldChange("courseDescription")}><FontAwesomeIcon icon={flag ? faCaretDown : faCaretUp} /></button></th>
                        <th> Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        courses.map(
                            course =>
                                <tr key={course.courseId}>
                                    <td> {course.courseName}</td>
                                    <td> {course.courseDuration}</td>
                                    <td> {course.courseDescription}</td>
                                    <td>
                                        <button className='btn btn-success me-2' onClick={() => navigate(`/edit-course/${course.courseId}`)}><FontAwesomeIcon icon={faPenToSquare} /></button>
                                        <button className='btn btn-danger'><FontAwesomeIcon icon={faTrash} /></button>
                                    </td>
                                </tr>
                        )
                    }
                </tbody>
            </table>
            <div className='d-flex justify-content-center align-items-baseline'>
                <button className="btn btn-primary btn-lg mr-2" onClick={() => showPrevPage()}><a>&laquo;</a></button>
                <Paginate pageSize={pageSize} dataSize={dataSize} paginate={paginate} />
                <button className="btn btn-primary btn-lg mr-2" onClick={() => showNextPage()}><a>&raquo;</a></button>
            </div>
        </>
    )
}

export default ViewAllCourses