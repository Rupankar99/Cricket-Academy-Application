import React, { useState, useEffect } from 'react'
import Academy_Service from '../../services/Academy_Service'
import AcademyCard from './AcademyCard'
import { faTrash, faPenToSquare, faArrowRight } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { NavLink } from 'react-router-dom'
import Paginate from '../Pages/Paginate'
const AcademyList = (props) => {
    const [academies, setAcademies] = useState([])
    const [pageNumber, setPageNumber] = useState(0)
    const [dataSize, setDataSize] = useState(null)

    const pageSize = 2

    const filteredData = academies.filter((academy) => {
        if (props.inp === "") {
            return academy;
        } else {
            return academy.instituteName.toLowerCase().includes(props.inp);
        }
    });

    const listAcademies = () => {
        Academy_Service.get(pageNumber, pageSize)
            .then(response => {
                console.log(response.data)
                setAcademies(response.data)
                Academy_Service.getCount()
                    .then((response) => {
                        setDataSize(response.data)
                    })
                    .catch((e) => {
                        console.log(e)
                    })
            })
            .catch(error => {
                console.log("Something went wrong " + error)
            })
    }
    useEffect(() => {
        listAcademies()
    }, [pageNumber])

    const handleDelete = (id) => {
        Academy_Service.delete(id)
            .then((response) => {
                listAcademies()
            })
            .catch((error) => {
                console.log(error)
            })
    }

    const paginate = (pagenum) => {
        setPageNumber(pagenum)
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
            <div className="row justify-content-md-center">
                {
                    filteredData.map(academy => (
                        <div className="card col col-lg-2 p-2 mx-3 mb-3" id="userAcademyGrid1" key={academy.instituteId}>
                            <AcademyCard name={academy.instituteName} address={academy.instituteAddress} icon={academy.imgUrl} email={academy.email} mobno={academy.mobile} />
                            <div className="d-flex justify-content-center">
                                <button className="btn btn-info me-3">
                                    <NavLink className="link-dark" to={`/edit-academy/${academy.instituteId}`}>
                                        <FontAwesomeIcon icon={faPenToSquare} />
                                    </NavLink>
                                </button>
                                <button className='btn btn-danger me-3' onClick={() => handleDelete(academy.instituteId)}><FontAwesomeIcon icon={faTrash} /></button>
                                <button className='btn btn-success' >
                                    <NavLink className="link-light" to={`/academy/${academy.instituteId}/courses`}>
                                        <FontAwesomeIcon icon={faArrowRight} />
                                    </NavLink>
                                </button>
                            </div>
                        </div>
                    ))
                }
            </div>
            <div className='d-flex justify-content-center align-items-baseline'>
                <button className="btn btn-primary btn-lg mr-2" onClick={() => showPrevPage()}><a>&laquo;</a></button>
                <Paginate pageSize={pageSize} dataSize={dataSize} paginate={paginate} />
                <button className="btn btn-primary btn-lg mr-2" onClick={() => showNextPage()}><a>&raquo;</a></button>
            </div>
        </>
    )
}

export default AcademyList