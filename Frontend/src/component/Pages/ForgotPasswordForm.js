import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { yupResolver } from '@hookform/resolvers/yup'
import { useForm } from 'react-hook-form'
import * as Yup from 'yup'
import ForgotPassword_Service from '../../services/ForgotPassword_Service'
const ForgotPasswordForm = () => {

    const schema = Yup.object().shape({
        email: Yup.string().email().required("Email is required"),
    })

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    })



    const [forgotPasswordToken, setForgotPasswordToken] = useState("")

    const submitForm = ({ email }) => {
        ForgotPassword_Service.sendEmail(email)
            .then((res) => {
                console.log(res.data)
                setForgotPasswordToken(res.data)
            })
    }

    return (
        <>
            <form className='container' onSubmit={handleSubmit(submitForm)}>
                <div className="form-group mb-4">
                    <label htmlFor="exampleInputEmail1" className='mb-2'>Email address</label>
                    <input type="email" className="form-control" name="email" placeholder="Enter email" {...register('email')} />
                    <p className='text-danger'> {errors.email?.message}</p>
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
                {
                    forgotPasswordToken ? <button className='btn btn-primary mt-3'><Link className='link-light text-decoration-none' to={`/reset-password/${forgotPasswordToken}`}>Click here to reset your password</Link></button> : ''
                }
            </form>
        </>
    )
}

export default ForgotPasswordForm