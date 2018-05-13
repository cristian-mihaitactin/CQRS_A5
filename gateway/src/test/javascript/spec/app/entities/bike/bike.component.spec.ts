/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { BikeComponent } from '../../../../../../main/webapp/app/entities/bike/bike.component';
import { BikeService } from '../../../../../../main/webapp/app/entities/bike/bike.service';
import { Bike } from '../../../../../../main/webapp/app/entities/bike/bike.model';

describe('Component Tests', () => {

    describe('Bike Management Component', () => {
        let comp: BikeComponent;
        let fixture: ComponentFixture<BikeComponent>;
        let service: BikeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [BikeComponent],
                providers: [
                    BikeService
                ]
            })
            .overrideTemplate(BikeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BikeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BikeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bike(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bikes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
