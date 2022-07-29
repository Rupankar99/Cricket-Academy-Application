import React from 'react'

const Paginate = ({ pageSize, dataSize, paginate }) => {

    let pageNumbers = []

    for (let i = 0; i < Math.ceil(dataSize / pageSize); i++) {
        pageNumbers.push(i)
    }
    return (
        <>
            <nav aria-label="Page navigation example" className='d-flex justify-content-center'>
                <ul class="pagination pagination-lg ">
                    {
                        pageNumbers.map((i) => (
                            <li class="page-item"><a class="page-link" href="#" onClick={() => paginate(i)}>{i + 1}</a></li>
                        ))
                    }
                </ul>
            </nav>
        </>
    )
}

export default Paginate