import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Activities} from './pages/activities/activities';
import { Register } from './register/register';

export const routes: Routes = [
  {
    path:'',
    redirectTo:'login',
    pathMatch:'full'
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'register',
    component: Register
  },
  {
    path:'activities',
    component: Activities
  }
];
