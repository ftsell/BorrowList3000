import { Router } from 'express'

const router = Router()
export const nuxtMiddleware = router;

router.get('/users', (req, res) => {
    res.json({ hello: 'world' })
})
