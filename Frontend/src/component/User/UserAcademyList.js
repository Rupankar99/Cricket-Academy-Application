import React, { useState, useEffect } from 'react'
import Academy_Service from '../../services/Academy_Service'
import Dcard from './Dcard'
import Paginate from '../Pages/Paginate'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowRight } from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router-dom'

const UserAcademyList = (props) => {
    const [academies, setAcademies] = useState([])
    const [pageNumber, setPageNumber] = useState(0)
    const [dataSize, setDataSize] = useState(null)

    const pageSize = 2
    const navigate = useNavigate()

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

    const paginate = (pagenum) => {
        setPageNumber(pagenum)
    }

    useEffect(() => {
        listAcademies()
    }, [pageNumber])

    return (
        <>
            <div class="row justify-content-md-center">
                {
                    filteredData.map(academy => (
                        <div class="card col col-lg-2 p-2 mx-3 mb-3" id="userAcademyGrid1" key={academy.instituteId}>
                            <Dcard name={academy.instituteName} address={academy.instituteAddress} icon={academy.img_url} email={academy.email} mobno={academy.mobile} />
                            <div className="d-flex justify-content-center">
                                <button className='btn btn-success' onClick={() => navigate(`/${academy.instituteId}/courses`)}><FontAwesomeIcon icon={faArrowRight} /></button>
                            </div>
                        </div>
                    ))
                }
            </div>
            <Paginate pageSize={pageSize} dataSize={dataSize} paginate={paginate} />
        </>
    )
}

export default UserAcademyList