import React from 'react'
import { useNavigate } from 'react-router-dom'
const AccessDenied = () => {
    const navigate = useNavigate()
    return (
        <div className='jumbotron vw-100 vh-100 bg-light'>
            <h1 className='display-4 text-center text-danger'>You do not have access to this page !!</h1>
            <p className='lead text-center'>
                <a className="btn btn-danger btn-lg mt-2" href="#" role="button" onClick={() => navigate(-2)}>Go Back</a>
            </p>
        </div>
    )
}

export default AccessDenied  