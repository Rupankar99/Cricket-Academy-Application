import React, { useEffect, useState } from 'react'
import Student_Service from '../../services/Student_Service'
import AdminNavbar from './AdminNavbar'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTrash, faPenToSquare, faCaretUp, faCaretDown } from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router-dom'
import Paginate from '../Pages/Paginate'

const StudentList = () => {

    const [students, setStudents] = useState([])
    const [dataSize, setDataSize] = useState(null)
    const [pageNumber, setPageNumber] = useState(0)
    const [sortBy, setSortBy] = useState("")
    const [sortDir, setSortDir] = useState("asc")
    const [flag, setFlag] = useState(true)

    const navigate = useNavigate()

    const pageSize = 3

    const listStudents = () => {
        Student_Service.getAllStudents(pageNumber, pageSize, sortBy, sortDir)
            .then((response) => {
                console.log(response.data)
                setStudents(response.data)
                Student_Service.count()
                    .then((res) => {
                        setDataSize(res.data)
                    })
                    .catch((err) => {
                        console.log(err)
                    })
            })
            .catch((e) => {
                console.log(e)
            })
    }

    const handleDelete = (id) => {
        Student_Service.delete(id)
            .then(() => {
                listStudents()
            })
            .catch((error) => {
                console.log(error)
            })
    }

    useEffect(() => {
        listStudents()
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
            <div className=''>
                <table className="table table-striped mx-auto bg-light w-75 mt-4">
                    <thead className='bg-dark text-white'>
                        <tr>
                            <th> Student Id <button className='btn btn-sm btn-dark' onClick={() => sortFieldChange("studentid")}><FontAwesomeIcon icon={flag ? faCaretDown : faCaretUp} /></button></th>
                            <th> Student Name <button className='btn btn-sm btn-dark' onClick={() => sortFieldChange("studentname")}><FontAwesomeIcon icon={flag ? faCaretDown : faCaretUp} /></button></th>
                            <th> Student Email <button className='btn btn-sm btn-dark' onClick={() => sortFieldChange("email")}><FontAwesomeIcon icon={flag ? faCaretDown : faCaretUp} /></button></th>
                            <th> Enrolled Course </th>
                            <th> Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            students.map(
                                student =>
                                    <tr key={student.admissionid}>
                                        <td> {student.studentid}</td>
                                        <td> {student.studentname}</td>
                                        <td> {student.email}</td>
                                        <td> {student.courses[0].courseName}</td>
                                        <td>
                                            <button className='btn btn-primary me-2' onClick={() => navigate(`/student-details/${student.admissionid}`)}>View</button>
                                            <button className='btn btn-success me-2' onClick={() => navigate(`/edit-student/${student.admissionid}`)}><FontAwesomeIcon icon={faPenToSquare} /></button>
                                            <button className='btn btn-danger' onClick={() => handleDelete(student.studentid)}><FontAwesomeIcon icon={faTrash} /></button>
                                        </td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
            <div className='d-flex justify-content-center align-items-baseline'>
                <button className="btn btn-primary btn-lg mr-2" onClick={() => showPrevPage()}><a>&laquo;</a></button>
                <Paginate pageSize={pageSize} dataSize={dataSize} paginate={paginate} />
                <button className="btn btn-primary btn-lg mr-2" onClick={() => showNextPage()}><a>&raquo;</a></button>
            </div>
        </>
    )
}

export default StudentList
