import { Component, OnInit ,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-activities',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './activities.html',
  styleUrl: './activities.css'
})
export class Activities implements OnInit {

  activities: any[] = [];
  errorMessage = '';
  title = '';
  description = '';
  completed = false;
  activityType = 'OTHER';
  startDate ='';
  endDate ='';
  status: 'PLANNED' | 'ACTIVE' | 'COMPLETED' = 'ACTIVE';
  searchText = '';
  statusFilter = 'ALL';
  sortOption = 'NEAREST';

  constructor(private http: HttpClient,private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.loadActivities();
  }

  loadActivities(): void {
    if (typeof window === 'undefined'){
      return;
    }
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    this.http
      .get<any[]>(
        'https://activity-tracker-backend-4erw.onrender.com/api/activities',
        { headers }
      )
      .subscribe({
        next: (response) => {
          this.activities = response;
          this.errorMessage = '';
        },
        error: (error) => {
          if (error.status === 400 && error.error?.message) {
            this.errorMessage = error.error.message;
          } else {
            this.errorMessage = 'Faaliyet eklenemedi.';
          }

          this.cdr.markForCheck();
        }
      });
  }

  addActivity(): void {
    if (!this.startDate || !this.endDate) {
      this.errorMessage = 'Başlangıç ve bitiş tarihi seçilmelidir.';
      return;
    }
    if (this.endDate < this.startDate) {
      this.errorMessage = 'Bitiş tarihi başlangıç tarihinden önce olamaz.';
      return;
    }
    if (!this.title.trim() || !this.description.trim()) {
      this.errorMessage =
        'Başlık ve açıklama alanlarını doldurmalısın.';
      return;
    }
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const body = {
      title: this.title,
      description: this.description,
      completed: this.completed,
      activityType: this.activityType,
      startDate:this.startDate,
      endDate:this.endDate
    };

    this.http
      .post<any>(
        'https://activity-tracker-backend-4erw.onrender.com/api/activities',
        body,
        { headers }
      )
      .subscribe({
        next: (createdActivity) => {
          this.activities = [
            ...this.activities,
            createdActivity
          ];

          this.title = '';
          this.description = '';
          this.completed = false;
          this.activityType = 'OTHER';
          this.startDate='';
          this.endDate='';
          this.errorMessage = '';
          this.cdr.markForCheck();
        },
        error: (error) => {
          this.errorMessage =
            error.error?.message || 'Faaliyet eklenemedi.';
        }
      });
  }
  get totalActivities(): number {
    return this.activities.length;
  }

  get plannedActivities(): number {
    return this.activities.filter(
      activity => activity.status === 'PLANNED'
    ).length;
  }

  get activeActivities(): number {
    return this.activities.filter(
      activity => activity.status === 'ACTIVE'
    ).length;
  }

  get completedActivities(): number {
    return this.activities.filter(
      activity => activity.status === 'COMPLETED'
    ).length;
  }
  get filteredActivities(): any[] {
    const filtered = this.activities.filter((activity: any) => {
      const title = activity.title?.toLowerCase() || '';
      const description = activity.description?.toLowerCase() || '';
      const search = this.searchText.toLowerCase();

      const matchesSearch =
        title.includes(search) ||
        description.includes(search);

      const matchesStatus =
        this.statusFilter === 'ALL' ||
        activity.status === this.statusFilter;

      return matchesSearch && matchesStatus;
    });

    return filtered.sort((a: any, b: any) => {

      const dateA = a.startDate ? new Date(a.startDate).getTime() : 0;
      const dateB = b.startDate ? new Date(b.startDate).getTime() : 0;

      if (this.sortOption === 'NEWEST') {
        return dateB - dateA;
      }

      if (this.sortOption === 'OLDEST') {
        return dateA - dateB;
      }

      return dateA - dateB;
    });
  }
  deleteActivity(id: number): void {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    this.http
      .delete(
        `https://activity-tracker-backend-4erw.onrender.com/api/activities/${id}`,
        { headers }
      )
      .subscribe({
        next: () => {
          this.activities = this.activities.filter(
            activity => activity.id !== id
          );

          this.errorMessage = '';

          this.cdr.markForCheck();
        },
        error: (error) => {
          this.errorMessage =
            error.error?.message || 'Faaliyet silinemedi.';
        }
      });
  }
  getActivityTypeLabel(type: string): string {
    const labels: Record<string, string> = {
      MEETING: 'Toplantı',
      DEVELOPMENT: 'Yazılım Geliştirme',
      BUG_FIX: 'Hata Düzeltme',
      TESTING: 'Test',
      CODE_REVIEW: 'Kod İnceleme',
      RESEARCH: 'Araştırma',
      DOCUMENTATION: 'Dokümantasyon',
      TRAINING: 'Eğitim',
      CUSTOMER_SUPPORT: 'Müşteri Desteği',
      OTHER: 'Diğer'
    };

    return labels[type] || 'Diğer';
  }
}
